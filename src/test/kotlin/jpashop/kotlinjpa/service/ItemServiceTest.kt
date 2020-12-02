package jpashop.kotlinjpa.service

import jpashop.kotlinjpa.domain.item.Book
import jpashop.kotlinjpa.repository.ItemRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ItemServiceTest {

	@Autowired
	lateinit var itemRepo: ItemRepository

	@Test
	fun saveItem() {
		//given
		val book = Book(1L, "name", 1000, 10, "author", "isbn")

		//when
		itemRepo.save(book)

		//then
		val findBook = itemRepo.findById(1L)
			.get()

		assertThat(book).isEqualTo(findBook)
	}
}
