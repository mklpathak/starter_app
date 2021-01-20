/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplemovieapp.MainRepository
import javax.inject.Inject

/**
 * Factory to create [HomeViewModel]
 */
class PostDetailViewModelFactory @Inject constructor(
    private val postDetailRepository: PostDetailRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != PostDetailViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return PostDetailViewModel(
            postDetailRepository
        ) as T
    }
}
