package jpashop.kotlinjpa.controller

import jpashop.kotlinjpa.domain.dto.OrderResponseDto
import jpashop.kotlinjpa.domain.dto.Result
import jpashop.kotlinjpa.service.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(val orderService: OrderService) {

	@GetMapping("/api/v3/orders")
	fun orderV3(): Result {
		val orderResponseDtos = orderService.findAllWithItem()
			.map { o -> OrderResponseDto(o) }
		return Result(orderResponseDtos)
	}

	@GetMapping("/api/v3.1/orders")
	fun orderV3_page(
		@RequestParam(value = "offset", defaultValue = "0") offset: Int,
		@RequestParam(value = "limit", defaultValue = "100") limit: Int,
	): Result {
		val orderResponseDtos = orderService.findAllWithMemberDelivery(offset, limit)
			.map { o -> OrderResponseDto(o) }
		return Result(orderResponseDtos)
	}

}
