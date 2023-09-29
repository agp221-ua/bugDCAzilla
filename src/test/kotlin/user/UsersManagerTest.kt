package user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UsersManagerTest{

    @Test
    fun testHash(){
        val hash = UsersManager.usersManager.hashPass("1234")
        val hash2 = UsersManager.usersManager.hashPass("1234")
        assertEquals(hash, hash2)
    }
}