/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
