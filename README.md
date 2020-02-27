# FreeFallDetection

In this project I detect free fall with accelerometer sensor, So this detection is differentiate a free fall from other kinds of device motion, like shaking, rotating, and moving.
My Solution is just detect the vertical fall so it means when the device fall from up to down the free fall happened. This solution is in the method "isFall" in the class "FallDetectionService.java". There is a problem here when you throw the device from down to up also free fall detected.

This program run in background so all the process do in the class which called FallDetectionService.java, this class extend from Service in Android. After free fall happened program push a local notification to notify the user that free fall happened. 
The program also calculate the duration of a free fall in milliSeconds. This calculation is base on start time and end time timestamp of fall divided by 1000000.

After free fall happened the duration of each fall save in Room database and retrive the saved value with ViewModel,LiveData pattern in a recyclerview.Although I have a list of fall duration in my app, I also expected when the value of fall duration produce in background the data appear in recyclerview as live data, it wans't, the data will appear after I close the app.I know where the problem is, if I can implement viewmodel inside of Service class everything will be ok.
