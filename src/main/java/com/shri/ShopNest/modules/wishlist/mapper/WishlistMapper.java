package com.shri.ShopNest.modules.wishlist.mapper;

import com.shri.ShopNest.modules.wishlist.dto.WishlistDto;
import com.shri.ShopNest.modules.wishlist.dto.WishlistItemDto;
import com.shri.ShopNest.modules.wishlist.model.Wishlist;
import com.shri.ShopNest.modules.wishlist.model.WishlistItem;

import java.util.ArrayList;
import java.util.List;

public class WishlistMapper {
    public static WishlistDto toDto(Wishlist wishlist) {
        List<WishlistItemDto> wishlistItemDtoList = new ArrayList<>();

        for (WishlistItem wishlistItem: wishlist.getItems()) {
            WishlistItemDto wishlistItemDto = WishlistItemDto.builder()
                    .id(wishlistItem.getId())
                    .wishlistId(wishlist.getId())
                    .notes(wishlistItem.getNotes())
                    .priority(wishlistItem.getPriority())
                    .addedAt(wishlistItem.getAddedAt())
                    .productId(wishlistItem.getProduct().getId())
                    .productName(wishlistItem.getProduct().getName())
                    .build();

            wishlistItemDtoList.add(wishlistItemDto);
        }

        return WishlistDto.builder()
                .name(wishlist.getName())
                .id(wishlist.getId())
                .wishlistItemDto(wishlistItemDtoList)
                .build();
    }
}
