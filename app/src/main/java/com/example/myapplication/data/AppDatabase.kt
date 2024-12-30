import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.myapplication.data.ImageDAO
import com.example.myapplication.data.ImageItem

@Database(entities = [ImageItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDAO
}
