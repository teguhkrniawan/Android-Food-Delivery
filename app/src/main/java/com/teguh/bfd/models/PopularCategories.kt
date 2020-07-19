package com.teguh.bfd.models

// model representasi dari MostPopular
class PopularCategories {
    var food_id: String = ""
    var menu_id: String = ""
    var name: String = ""
    var image: String = ""

    constructor()

    constructor(food_id: String, menu_id: String, name: String, image: String) {
        this.food_id = food_id
        this.menu_id = menu_id
        this.name = name
        this.image = image
    }
}