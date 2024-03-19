package com.example.kas.di

import android.app.Application
import androidx.room.Room
import com.example.kas.kas_note.data.data_source.KasDatabase
import com.example.kas.kas_note.data.repository.KasRepositoryImpl
import com.example.kas.kas_note.domain.repository.KasRepository
import com.example.kas.kas_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): KasDatabase {
        return Room.databaseBuilder(
            app,
            KasDatabase::class.java,
            KasDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: KasDatabase): KasRepository {
        return KasRepositoryImpl(db.kasDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: KasRepository): KasUseCases {
        return KasUseCases(
            getKases = GetKases(repository),
            deleteKas = DeleteKas(repository),
            addKas = AddKas(repository),
            getKas = GetKas(repository)
        )
    }
}