package com.mark.starwars.utils

import com.mark.starwars.data.db.Repository
import com.mark.starwars.data.model.Character
import io.reactivex.Single

fun Character.isAlreadyAdded(repository: Repository) : Single<Int> {
    return repository.isAlreadyExists(this)
}