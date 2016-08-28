# Go Video Yoself
"Entry point": HomeActivity  

## Prerequisites
* Java 8
* Android Studio

## Architectural info
* Activity: Manages current View/Scene state, decides which View is showing when, reacts to events.
* Fragment: Views.
* Source: The Single Source of Truth for data, i.e. the data layer and the only entry for accessing data.
* Behavior: Encapsulates complex behaviors in the app. Ex. the VideoBehavior manages ->open camera->listen for recorded video URI->save video to VideoSource (who uploads to API). This "flow" span several lifecycles, like two Activites, several Fragments and Threads - the VideoBehavior gathers all this code into one visually sequential block of code.
* Based on Uncle Bobs Clean Architecture, not unlike MVP but with the VP combined. View manages its state and the UX, RxJava allows for us to write code like this in a very declarative and sequential manner. 

