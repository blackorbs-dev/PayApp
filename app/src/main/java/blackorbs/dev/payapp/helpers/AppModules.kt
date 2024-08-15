/*
 * Copyright 2024 BlackOrbs (blackorbs@icloud.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package blackorbs.dev.payapp.helpers

import android.content.Context
import androidx.room.Room
import blackorbs.dev.payapp.database.BaseRepository
import blackorbs.dev.payapp.database.LocalDatabase
import blackorbs.dev.payapp.database.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(repository: Repository) : BaseRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Singleton
    @Provides
    fun provideDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context) : LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, "transaction_db").build()

    @Singleton
    @Provides
    fun provideTransactionDao(localDatabase: LocalDatabase) = localDatabase.transactionDao()
}