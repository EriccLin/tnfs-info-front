package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.EvidenceDto;
import com.tnfs.infoApplication.dto.ItemDto;
import com.tnfs.infoApplication.model.EvidenceObj;
import com.tnfs.infoApplication.model.ItemObj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class IEvidenceObjMapperImpl implements IEvidenceObjMapper {

    @Override
    public EvidenceDto toDto(EvidenceObj obj) {
        if ( obj == null ) {
            return null;
        }

        EvidenceDto evidenceDto = new EvidenceDto();

        evidenceDto.setItems( itemObjListToItemDtoList( obj.getItems() ) );
        evidenceDto.setId( obj.getId() );
        evidenceDto.setCriminalCaseId( obj.getCriminalCaseId() );
        evidenceDto.setLocation( obj.getLocation() );
        evidenceDto.setType( obj.getType() );
        evidenceDto.setCode( obj.getCode() );
        evidenceDto.setReserve( obj.getReserve() );

        return evidenceDto;
    }

    @Override
    public List<EvidenceDto> toDto(List<EvidenceObj> list) {
        if ( list == null ) {
            return null;
        }

        List<EvidenceDto> list1 = new ArrayList<EvidenceDto>( list.size() );
        for ( EvidenceObj evidenceObj : list ) {
            list1.add( toDto( evidenceObj ) );
        }

        return list1;
    }

    @Override
    public Set<EvidenceDto> toDto(Set<EvidenceObj> set) {
        if ( set == null ) {
            return null;
        }

        Set<EvidenceDto> set1 = new HashSet<EvidenceDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( EvidenceObj evidenceObj : set ) {
            set1.add( toDto( evidenceObj ) );
        }

        return set1;
    }

    @Override
    public EvidenceObj toObj(EvidenceDto dto) {
        if ( dto == null ) {
            return null;
        }

        EvidenceObj evidenceObj = new EvidenceObj();

        evidenceObj.setItems( itemDtoListToItemObjList( dto.getItems() ) );
        evidenceObj.setId( dto.getId() );
        evidenceObj.setCriminalCaseId( dto.getCriminalCaseId() );
        evidenceObj.setLocation( dto.getLocation() );
        evidenceObj.setType( dto.getType() );
        evidenceObj.setCode( dto.getCode() );
        evidenceObj.setReserve( dto.getReserve() );

        return evidenceObj;
    }

    @Override
    public List<EvidenceObj> toObj(List<EvidenceDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<EvidenceObj> list = new ArrayList<EvidenceObj>( dtoList.size() );
        for ( EvidenceDto evidenceDto : dtoList ) {
            list.add( toObj( evidenceDto ) );
        }

        return list;
    }

    @Override
    public Set<EvidenceObj> toObj(Set<EvidenceDto> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<EvidenceObj> set = new HashSet<EvidenceObj>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( EvidenceDto evidenceDto : dtoSet ) {
            set.add( toObj( evidenceDto ) );
        }

        return set;
    }

    protected ItemDto itemObjToItemDto(ItemObj itemObj) {
        if ( itemObj == null ) {
            return null;
        }

        ItemDto itemDto = new ItemDto();

        itemDto.setId( itemObj.getId() );
        itemDto.setEvidenceId( itemObj.getEvidenceId() );
        itemDto.setName( itemObj.getName() );
        itemDto.setQuantity( itemObj.getQuantity() );
        itemDto.setQualifier( itemObj.getQualifier() );
        itemDto.setReserve( itemObj.getReserve() );

        return itemDto;
    }

    protected List<ItemDto> itemObjListToItemDtoList(List<ItemObj> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemDto> list1 = new ArrayList<ItemDto>( list.size() );
        for ( ItemObj itemObj : list ) {
            list1.add( itemObjToItemDto( itemObj ) );
        }

        return list1;
    }

    protected ItemObj itemDtoToItemObj(ItemDto itemDto) {
        if ( itemDto == null ) {
            return null;
        }

        ItemObj itemObj = new ItemObj();

        itemObj.setId( itemDto.getId() );
        itemObj.setEvidenceId( itemDto.getEvidenceId() );
        itemObj.setName( itemDto.getName() );
        itemObj.setQuantity( itemDto.getQuantity() );
        itemObj.setQualifier( itemDto.getQualifier() );
        itemObj.setReserve( itemDto.getReserve() );

        return itemObj;
    }

    protected List<ItemObj> itemDtoListToItemObjList(List<ItemDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemObj> list1 = new ArrayList<ItemObj>( list.size() );
        for ( ItemDto itemDto : list ) {
            list1.add( itemDtoToItemObj( itemDto ) );
        }

        return list1;
    }
}
