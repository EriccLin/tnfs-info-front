package com.tnfs.infoApplication.mapper;

import com.tnfs.infoApplication.dto.CriminalCaseDto;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class ICriminalCaseObjMapperImpl implements ICriminalCaseObjMapper {

    private final IPersonInvolvedObjMapper iPersonInvolvedObjMapper = Mappers.getMapper( IPersonInvolvedObjMapper.class );
    private final ITimeLocalityObjMapper iTimeLocalityObjMapper = Mappers.getMapper( ITimeLocalityObjMapper.class );
    private final IInvestigationObjMapper iInvestigationObjMapper = Mappers.getMapper( IInvestigationObjMapper.class );
    private final IInChargeObjMapper iInChargeObjMapper = Mappers.getMapper( IInChargeObjMapper.class );
    private final IEvidenceObjMapper iEvidenceObjMapper = Mappers.getMapper( IEvidenceObjMapper.class );
    private final ICaseTypeObjMapper iCaseTypeObjMapper = Mappers.getMapper( ICaseTypeObjMapper.class );

    @Override
    public CriminalCaseDto toDto(CriminalCaseObj obj) {
        if ( obj == null ) {
            return null;
        }

        CriminalCaseDto criminalCaseDto = new CriminalCaseDto();

        criminalCaseDto.setId( obj.getId() );
        criminalCaseDto.setCode( obj.getCode() );
        criminalCaseDto.setName( obj.getName() );
        criminalCaseDto.setContent( obj.getContent() );
        criminalCaseDto.setIsActive( obj.getIsActive() );
        criminalCaseDto.setCreatedAt( obj.getCreatedAt() );
        criminalCaseDto.setUpdatedAt( obj.getUpdatedAt() );
        criminalCaseDto.setReserve( obj.getReserve() );
        criminalCaseDto.setInCharges( iInChargeObjMapper.toDto( obj.getInCharges() ) );
        criminalCaseDto.setPersonInvolveds( iPersonInvolvedObjMapper.toDto( obj.getPersonInvolveds() ) );
        criminalCaseDto.setInvestigations( iInvestigationObjMapper.toDto( obj.getInvestigations() ) );
        criminalCaseDto.setEvidences( iEvidenceObjMapper.toDto( obj.getEvidences() ) );
        criminalCaseDto.setTimeLocalities( iTimeLocalityObjMapper.toDto( obj.getTimeLocalities() ) );
        criminalCaseDto.setCaseTypes( iCaseTypeObjMapper.toDto( obj.getCaseTypes() ) );

        return criminalCaseDto;
    }

    @Override
    public List<CriminalCaseDto> toDto(List<CriminalCaseObj> objList) {
        if ( objList == null ) {
            return null;
        }

        List<CriminalCaseDto> list = new ArrayList<CriminalCaseDto>( objList.size() );
        for ( CriminalCaseObj criminalCaseObj : objList ) {
            list.add( toDto( criminalCaseObj ) );
        }

        return list;
    }

    @Override
    public CriminalCaseObj toObj(CriminalCaseDto dto) {
        if ( dto == null ) {
            return null;
        }

        CriminalCaseObj criminalCaseObj = new CriminalCaseObj();

        criminalCaseObj.setId( dto.getId() );
        criminalCaseObj.setName( dto.getName() );
        criminalCaseObj.setCode( dto.getCode() );
        criminalCaseObj.setContent( dto.getContent() );
        criminalCaseObj.setIsActive( dto.getIsActive() );
        criminalCaseObj.setReserve( dto.getReserve() );
        criminalCaseObj.setCreatedAt( dto.getCreatedAt() );
        criminalCaseObj.setUpdatedAt( dto.getUpdatedAt() );
        criminalCaseObj.setInCharges( iInChargeObjMapper.toObj( dto.getInCharges() ) );
        criminalCaseObj.setPersonInvolveds( iPersonInvolvedObjMapper.toObj( dto.getPersonInvolveds() ) );
        criminalCaseObj.setInvestigations( iInvestigationObjMapper.toObj( dto.getInvestigations() ) );
        criminalCaseObj.setEvidences( iEvidenceObjMapper.toObj( dto.getEvidences() ) );
        criminalCaseObj.setTimeLocalities( iTimeLocalityObjMapper.toObj( dto.getTimeLocalities() ) );
        criminalCaseObj.setCaseTypes( iCaseTypeObjMapper.toObj( dto.getCaseTypes() ) );

        return criminalCaseObj;
    }

    @Override
    public List<CriminalCaseObj> toObj(List<CriminalCaseDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CriminalCaseObj> list = new ArrayList<CriminalCaseObj>( dtoList.size() );
        for ( CriminalCaseDto criminalCaseDto : dtoList ) {
            list.add( toObj( criminalCaseDto ) );
        }

        return list;
    }
}
