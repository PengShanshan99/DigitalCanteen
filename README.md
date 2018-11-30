# DigitalCanteen
For Introduction to InforSystems and Programming course.


Our Firebase link https://digitalcanteen-471fa.firebaseio.com/

How to implement firebase realtime database (to store the text information):
https://firebase.google.com/docs/database/android/start/

How to implement firebase storage database (to store image information):
https://firebase.google.com/docs/storage/android/start

How to get real time UI update from firebase, using recyclerView and CardView:
https://github.com/firebase/FirebaseUI-Android/blob/master/database/README.md

How I mapped the food with the images (i.e. One in Storage, one in RealtimeDatabase, how do I identify which photo is of which food?)
by having the same id as key.
When saving to database, there is an unique id for each food, the same id applies to the image storage. 
Hence, when I inflate the recyclerView from realtime database, I can refer to the id and hence find the corresponding image, displaying it
in the cardView using GlideAPP.

How to use Fragments for bottom navigation activity:
https://www.youtube.com/watch?v=jpaHMcQDaDg
https://www.simplifiedcoding.net/bottom-navigation-android-example/#Using-Bottom-Navigation-in-Android

How to inflate RecyclerView in a Fragment:
https://stackoverflow.com/questions/43104485/how-to-change-fragment-with-the-bottom-navigation-activity
