package jpashop.kotlinjpa.controller

import jpashop.kotlinjpa.domain.dto.OrderResponseDto
import jpashop.kotlinjpa.domain.dto.Result
import jpashop.kotlinjpa.service.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(val orderService: OrderService) {

	@GetMapping("/api/v3/orders")
	fun orderV3(): Result {
		val orderResponseDtos = orderService.findAllWithItem().map{ o -> OrderResponseDto(o) }
		return Result(orderResponseDtos)
	}

}
