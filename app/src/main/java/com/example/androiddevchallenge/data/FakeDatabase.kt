package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.model.PetModel

object FakeDatabase {
    val data = listOf(
        PetModel(
            name = "Ace",
            breed = "Bulldog",
            image = R.drawable.bulldog,
        ),
        PetModel(
            name = "Leo",
            breed = "Bengal Cat",
            image = R.drawable.bengal_cat,
        ),
        PetModel(
            name = "Milo",
            breed = "Abyssinian",
            image = R.drawable.abyssinian,
        ),
        PetModel(
            name = "Bandit",
            breed = "German Shepherd",
            image = R.drawable.german_shepherd,
        ),
        PetModel(
            name = "Charlie",
            breed = "Peterbald",
            image = R.drawable.peterbald,
        ),
        PetModel(
            name = "Hunter",
            breed = "Siberian husky",
            image = R.drawable.siberian_husky,
        ),
        PetModel(
            name = "Oliver",
            breed = "White Persian",
            image = R.drawable.white_persian_cat,
        ),
    )
}