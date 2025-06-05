
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Item(val name: String, val price: Double)

fun main() = runBlocking {
    val cartItems = MutableSharedFlow<List<Item>>()

    // print the cart on SharedFlow
    launch {
        cartItems.collect { items ->
            println("Cart Updated: $items")
        }
    }

    // trigger order confirmation when the cart is full
    launch {
        cartItems.filter { it.size >= 3 } // Filter for 3 or more items
            .onEach { println("Order Confirmation triggered!") }
            .launchIn(this)
    }

    // simulate adding items to the cart
    launch {
        var updatedItems = listOf(Item("Apple", 1.50))
        cartItems.emit(updatedItems)
        delay(1000)

        updatedItems = updatedItems + Item("Milk", 2.00)
        cartItems.emit(updatedItems)
        delay(1000)

        updatedItems = updatedItems + Item("Bread", 3.00)
        cartItems.emit(updatedItems)
    }

    delay(5000)
    coroutineContext.cancelChildren()
}
