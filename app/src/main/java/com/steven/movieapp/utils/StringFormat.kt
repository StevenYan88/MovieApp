package com.steven.movieapp.utils

import com.steven.movieapp.model.Actor

/**
 * Description:
 * Data：2019/1/30
 * Actor:Steven
 */

class StringFormat {
    companion object {
        fun formatGenres(genres: List<String>): String {
            return if (!genres.isNullOrEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in genres.indices)
                    if (i < genres.size - 1) {
                        stringBuilder.append(genres[i]).append("/")
                    } else {
                        stringBuilder.append(genres[i])
                    }
                stringBuilder.toString()
            } else {
                "不知名类型"
            }
        }


        fun formatPubdate(pubdates: List<String>): String {
            return if (!pubdates.isNullOrEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in pubdates.indices)
                    if (i < pubdates.size - 1) {
                        stringBuilder.append(pubdates[i]).append("/")
                    } else {
                        stringBuilder.append(pubdates[i])
                    }
                stringBuilder.toString()
            } else {
                "未知"
            }
        }

        fun formatDurations(durations: List<String>): String {
            return if (!durations.isNullOrEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in durations.indices)
                    if (i < durations.size - 1) {
                        stringBuilder.append(durations[i]).append("/")
                    } else {
                        stringBuilder.append(durations[i])
                    }
                stringBuilder.toString()
            } else {
                "未知"
            }
        }

        /**
         * 格式化导演、主演名字
         */
        fun formatName(authors: List<Actor>?): String {
            return if (authors != null && authors.isNotEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in authors.indices) {
                    if (i < authors.size - 1) {
                        stringBuilder.append(authors[i].name).append(" / ")
                    } else {
                        stringBuilder.append(authors[i].name)
                    }
                }
                stringBuilder.toString()

            } else {
                "佚名"
            }
        }

        /**
         * 格式化电影类型
         */
        fun formatCountry(country: List<String>): String {
            return if (country.isNotEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in country.indices) {
                    if (i < country.size - 1) {
                        stringBuilder.append(country[i]).append(" / ")
                    } else {
                        stringBuilder.append(country[i])
                    }
                }
                stringBuilder.toString()

            } else {
                "不知名国家"
            }
        }
    }


}