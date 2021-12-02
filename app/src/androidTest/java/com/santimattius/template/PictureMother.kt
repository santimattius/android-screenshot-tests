package com.santimattius.template

object PictureMother {

    fun createPictures() = (0..5).map {
        PictureTestModel(
            id = it.toString(),
            author = it.toString(),
            url = "https://www.pexels.com/photo/photo-of-pod-of-dolphins-2422915/",
            height = 250,
            width = 250,
            downloadUrl = "https://images.pexels.com/photos/2422915/pexels-photo-2422915.jpeg?auto=compress&cs=tinysrgb&h=350"
        )
    }

    fun picture(): PictureTestModel {
        val identifier = "${(0..10).random()}"
        return PictureTestModel(
            id = identifier,
            author = identifier,
            url = "https://www.pexels.com/photo/photo-of-pod-of-dolphins-2422915/",
            height = 250,
            width = 250,
            downloadUrl = "https://images.pexels.com/photos/2422915/pexels-photo-2422915.jpeg?auto=compress&cs=tinysrgb&h=350"
        )
    }
}

