package org.example.data

data class PokemonRequest(
    /**
     * Human-readable name typed/selected in UI.
     */
    val name: String,

    /**
     * Optional stable identifier from the product list (e.g. data-product-id / buy-button-XXXXX).
     * If provided, backend should prefer this for exact matching.
     */
    val productId: String? = null,

    /**
     * Optional absolute/relative product URL if UI has it.
     */
    val productUrl: String? = null
)
