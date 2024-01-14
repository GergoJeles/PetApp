package com.android_advanced_assignement_gj.petdb.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import com.android_advanced_assignement_gj.petdb.model.Pet



@Database(entities = [Pet::class], version = 2)
@TypeConverters(Converters::class)
abstract class PetDatabase : RoomDatabase(){

    abstract fun getPetDao() : PetDAO

    companion object{

        @Volatile
        private var instance: PetDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }
        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                PetDatabase::class.java,
                "pet_db"
            ).fallbackToDestructiveMigration().build()

    }
}

@Dao
interface PetDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet)


    @Query("SELECT * FROM Pets ORDER BY id DESC")
    fun getAllPets(): LiveData<List<Pet>>

    @Query("SELECT * FROM Pets WHERE petName LIKE:query OR petOwnerName LIKE:query")
    fun searchPet(query: String?): LiveData<List<Pet>>
}