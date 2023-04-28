package com.sharma.ankitapp.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sharma.ankitapp.model.response.Data
import com.sharma.ankitapp.ui.home.model.SubjectList
import com.sharma.ankitapp.utils.await

object Repository {

    var firebaseAuth: FirebaseAuth? = null
    var storage: FirebaseStorage
    var storageReference: StorageReference
    var rootNode: FirebaseDatabase
    var referenceCAcategory: DatabaseReference
    //val apiService = ApiService.api

    init {
        firebaseAuth = FirebaseAuth.getInstance()    //Authentication
        storage = FirebaseStorage.getInstance()   //File/image uploads
        storageReference = storage.reference    //Reference to the storage
        rootNode = FirebaseDatabase.getInstance()   // Realtime DB
        referenceCAcategory = rootNode.getReference("CAcategory") //Reference to DBTable
    }

    object Auth {
        suspend fun login(email: String, pwd: String): Data<FirebaseUser> {
            return try {
                val result = firebaseAuth?.signInWithEmailAndPassword(email, pwd)?.await()
                Data.SUCCESS(result?.user!!, result.user!!.uid)
            } catch (e: Exception) {
                e.printStackTrace()
                Data.ERROR(e)
            }
        }

        suspend fun signup(name: String, email: String, password: String): Data<FirebaseUser> {
            return try {
                val result = firebaseAuth?.createUserWithEmailAndPassword(email, password)?.await()
                result?.user?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                )?.await()
                Data.SUCCESS(result?.user!!, "")
            } catch (e: Exception) {
                e.printStackTrace()
                Data.ERROR(e)
            }
        }

        fun logout() {
            firebaseAuth?.signOut()
        }

        suspend fun getUserDetails(): Data<FirebaseUser> {
            return try {
                val result = firebaseAuth?.currentUser
                Data.SUCCESS(result!!, "")
            } catch (e: Exception) {
                e.printStackTrace()
                Data.ERROR(e)
            }
        }
    }

    object GetData {
        suspend fun getSubjectList(): Data<Any> {

            return try {
                val result = referenceCAcategory.get().await().value
                Log.i("TAG", "getSubjectList: $result")
                Data.SUCCESS(result!!, "")

            } catch (e: Exception) {
                e.printStackTrace()
                Data.ERROR(e)
            }
        }

    }


}