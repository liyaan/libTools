package com.liyaan.myffmepgfirst.carhome.mapper

import com.liyaan.myffmepgfirst.carhome.entity.SubjectEntity
import com.liyaan.myffmepgfirst.carhome.model.SubjectDataDataX
import com.liyaan.myffmepgfirst.carhome.model.SubjectListEntity

class EntityItemModelMapper:Mapper<SubjectEntity,SubjectDataDataX> {
    override fun map(input: SubjectEntity): SubjectDataDataX {
       return SubjectDataDataX(id=input.id,
            title = input.title,link = input.link,envelopePic = input.envelopePic,projectLink = input.projectLink)
    }
}