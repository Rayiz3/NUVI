import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageResId: Int, // Use resource IDs for now; use URI for dynamic storage.
    val title: String,
    val address: String,
    val description: String
)