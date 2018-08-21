First note: Github creates, "README.md", but the challenge said straight, "README", so I'm not sure if I should be using markdown as I go or not.

Initial arch:
- Dagger, Retrofit, Glide, AppCompat

I could have used Android Jetpack here, but given the time contraints, I'm going with what I know works well.

While not specified, I'm going to set the min target to 19 (KitKat) for highest overall compatibility.

Arch decision: Limiting arch to only activities, as fragments will use additional overhead, more complex lifecycle, and open the possibility for additional errors and edge cases. Normally I would arch using fragments, but with this current time constraint, I'm going to with only activities.

AppCompat is still in Beta for API 28, setting api target to 27 for now, so I don't hit unknown roadblocks.

Requirements don't specify what the starting/main activity should consist of. It also does not specify if users are supposed to be able to search, only that the API returns images. "Utilizing the Android framework, design a mobile app that will show a series of images as a gallery, fetched from Flickr's API." Based on time constraints I will create a simple search, as well as auto-loading images.

The technical test not allowing me to copy the API_KEY out of the provided URL because copy/paste = cheating in this test is really annoying.

Using recyclerview for the list so it scales.

Adding pagination against the API for future scalability.

"Image, Image Size, Image Dimensions, Title". What is the difference between size and dimensions? Like data-size in KB/MB, etc? Also, Flickr responds with ~10 size (dimension) formats when requesting getSizes() in the API. Do I just use original here? Or can I get away with just specifying the w/h of the imageView I'm using in my recycler view? I'm very detail-oriented, this lack of detail really bothers me.

Do I need to display these images when selected at all? It seems bare-bones without it. I want to add a ViewPager setup, time permitting.

Adding overlay to make images with titles look nicer.

Going with a dialog pop-up since I used a column view to make it look more like an image gallery, since the list view was, well, ugly.

Time is running out, I will not be able to finish the display of file size and what-not. Thought it is already stored


