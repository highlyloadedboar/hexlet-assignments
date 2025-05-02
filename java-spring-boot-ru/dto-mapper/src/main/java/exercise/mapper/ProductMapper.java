package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

// BEGIN
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    @Mapping(target = "cost", source = "price")
    public abstract void updateFromDto(ProductUpdateDTO dto, @MappingTarget Product entity);

    @Mapping(target = "cost", source = "price")
    @Mapping(target = "barcode", source = "vendorCode")
    @Mapping(target = "name", source = "title")
    public abstract Product toEntity(ProductCreateDTO dto);

    @Mapping(target = "cost", source = "price")
    @Mapping(target = "barcode", source = "vendorCode")
    @Mapping(target = "name", source = "title")
    public abstract Product toEntity(ProductDTO dto);

    @Mapping(target = "price", source = "cost")
    public abstract ProductUpdateDTO toUpdateDto(Product entity);

    @Mapping(target = "price", source = "cost")
    @Mapping(target = "vendorCode", source = "barcode")
    @Mapping(target = "title", source = "name")
    public abstract ProductDTO toDto(Product entity);

    @Mapping(target = "price", source = "cost")
    @Mapping(target = "vendorCode", source = "barcode")
    @Mapping(target = "title", source = "name")
    public abstract ProductCreateDTO toCreateDto(Product entity);
}
// END
