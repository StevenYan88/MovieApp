/*
 * Copyright 2018 Google LLC
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

package com.steven.movieapp.utils

import android.content.Context
import com.steven.movieapp.db.AppDatabase
import com.steven.movieapp.db.History
import com.steven.movieapp.repository.HistoryRepository
import com.steven.movieapp.viewmodel.HistoryViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */

object InjectorUtils {


    fun saveHistory(context: Context, history: History) {
        AppExecutors.diskIO().execute {
            HistoryRepository.getInstance(AppDatabase.getInstance(context).historyDao()).saveHistory(history)

        }
    }

    private fun getSearchMoveRepository(context: Context): HistoryRepository {
        return HistoryRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).historyDao()
        )
    }


    fun providerHistoryViewModelFactory(
        context: Context
    ): HistoryViewModelFactory {
        val repository = getSearchMoveRepository(context)
        return HistoryViewModelFactory(repository)
    }
}
