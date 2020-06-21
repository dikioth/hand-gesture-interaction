import os
import numpy as np
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Dropout, GlobalAveragePooling2D, Activation, MaxPool2D
from tensorflow.keras.layers import Flatten, Dense
from tensorflow.keras.models import load_model
from tensorflow.keras.callbacks import ModelCheckpoint, History
import cv2

config = tf.compat.v1.ConfigProto(gpu_options=tf.compat.v1.GPUOptions(
    per_process_gpu_memory_fraction=0.8)
    # device_count = {'GPU': 1}
)
config.gpu_options.allow_growth = True
session = tf.compat.v1.Session(config=config)
tf.compat.v1.keras.backend.set_session(session)


# Load dataset 2: Entire frames
LOAD_DATASET_NUM = 2

if LOAD_DATASET_NUM == 1:
    pass
elif LOAD_DATASET_NUM == 2:
    Xtrain = np.load('data/96x96_Xtrain.npy')/255.0
    Ytrain = (np.load('data/96x96_Ytrain.npy') - 48) / 48
    Xtest  = np.load('data/96x96_Xtest.npy')/255.0
    Ytest  = (np.load('data/96x96_Ytest.npy') - 48) / 48
    
print(Xtrain.shape)
print(Ytrain.shape)
print(Xtest.shape)
print(Ytest.shape)


# If wanted. Convert to gray color

Xtrain = np.array([cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) for frame in Xtrain], dtype=np.float32).reshape(8960,96,96,1)
Xtest = np.array([cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) for frame in Xtest], dtype=np.float32).reshape(2240,96,96,1)

print(f"Xtrain: {Xtrain.shape}")
print(f"Xtest: {Xtrain.shape}")

# Main model
def CNN_model():
    model = Sequential()
    
    model.add(Conv2D(16, (3,3), padding='same', activation='relu', input_shape=Xtrain.shape[1:])) 
    model.add(MaxPooling2D(pool_size=2))
    
    model.add(Conv2D(32, (3,3), padding='same', activation='relu'))
    model.add(MaxPooling2D(pool_size=2))
    
    model.add(Conv2D(64, (3,3), padding='same', activation='relu'))
    model.add(MaxPooling2D(pool_size=2))
    
    model.add(Conv2D(128, (3,3), padding='same', activation='relu'))
    model.add(MaxPooling2D(pool_size=2))
    
    model.add(Conv2D(256, (3,3), padding='same', activation='relu'))
    model.add(MaxPooling2D(pool_size=2))
    
    # Convert all values to 1D array
    model.add(Flatten())
    
    model.add(Dense(512, activation='relu'))
    model.add(Dropout(0.2))

    model.add(Dense(80))
    
    return model


# Prepare model
epochs = 5000
batch_size = 64
checkpoint_file = 'checkpoint.hdf5'
model_file_name = 'model.h5'

# Load saved model or create a new one
if checkpoint_file in os.listdir('models'):
    model = load_model(os.path.join('models', checkpoint_file))
elif model_file_name in os.listdir('models'):
    model = load_model(os.path.join('models', model_file_name))
else:
    model = CNN_model()
    
hist = History()
checkpointer = ModelCheckpoint(
    filepath=checkpoint_file, verbose=1, save_best_only=True)
model.compile(optimizer='adam', loss='mean_squared_error',
              metrics=['accuracy'])
# model_fit = model.fit(X_train, Y_train, validation_split=0.2, epochs=epochs, batch_size=batch_size, callbacks=[checkpointer, hist], verbose=1)
model_fit = model.fit(Xtrain, Ytrain, validation_data=(Xtest, Ytest), epochs=epochs, batch_size=batch_size, callbacks=[checkpointer, hist], verbose=1)

model.save(os.path.join('models', model_file_name))