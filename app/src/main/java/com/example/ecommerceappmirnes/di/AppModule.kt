package com.example.ecommerceappmirnes.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.core.app.ServiceCompat
import com.example.ecommerceappmirnes.firebase.FirebaseCommon
import com.example.ecommerceappmirnes.util.Constants.INTRODUCTION_SP
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.storage.FirebaseStorage
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
    fun provideFirebaseAuth()=FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDatabase()=com.google.firebase.ktx.Firebase.firestore

    @Provides
    @Singleton
    fun provideIntroductionSP(
        application: Application
    )= application.getSharedPreferences(INTRODUCTION_SP,MODE_PRIVATE)
    @Provides
    @Singleton
    fun provideFrebaseCommon(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    )= FirebaseCommon(firestore,firebaseAuth)

    @Provides
    @Singleton
    fun provideStorage() = FirebaseStorage.getInstance().reference
}