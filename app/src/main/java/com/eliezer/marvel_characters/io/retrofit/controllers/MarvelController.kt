package com.eliezer.marvel_characters.io.retrofit.controllers

import com.eliezer.marvel_characters.io.retrofit.services.CharacterService

class MarvelController(
    private var characterService: CharacterService
) {
    fun findCharacter(name : String) =characterService.listCharacter(name)
}