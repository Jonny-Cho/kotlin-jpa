package jpashop.kotlinjpa.domain.item

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AlbumTest {
	@Test
	fun albumTest() {
		val album = Album(0, "itemname", 1000, 10, "album artist", "album etc")
		assertThat(album.name).isEqualTo("itemname")
		assertThat(album.artist).isEqualTo("album artist")
	}
}
