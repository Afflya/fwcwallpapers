package com.afflyas.fwcwallpapers.repository

/**
 * Status of a data from repository that is provided to the UI.
 *
 * Created by the MovieRepository that returns
 * `LiveData<RepoResponse<MovieEntity>>` to pass back the data to the UI with its status.
 */
enum class RepoResponseStatus {
    SUCCESS,
    ERROR,
    LOADING
}