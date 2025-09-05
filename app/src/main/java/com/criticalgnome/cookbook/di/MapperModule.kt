package com.criticalgnome.cookbook.di

import com.criticalgnome.data.mapper.RecipeMapper
import com.criticalgnome.data.mapper.RecipeMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindRecipeMapper(impl: RecipeMapperImpl): RecipeMapper
}
