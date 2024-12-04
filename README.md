## Description
I have successfully implemented an application that extracts text from images with the help of Firebase ML kit.
<br>
The text extraction is accurate most of the time.

## Videos & Screenshots


https://github.com/user-attachments/assets/efd4f062-ca68-459e-8bb8-624334b301ac



## Features
* Upload From Gallery
* Take Pictures From Camera

## Missing Feature
I tried using Firebase object detection to focus only on LED displays but turns out Firebase's ML kit is only capable of detecting about 5 types of objects.
<br>
Then I tried using some TensorFlow models. A model I found on GitHub was excatly what I was looking for, but that model was unfortunately not compatible with Firebase
kit's dimension requirements.
