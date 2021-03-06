/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.soul.admin.service;

import org.dromara.soul.admin.dto.BatchCommonDTO;
import org.dromara.soul.admin.dto.SoulDictDTO;
import org.dromara.soul.admin.entity.SoulDictDO;
import org.dromara.soul.admin.mapper.SoulDictMapper;
import org.dromara.soul.admin.service.impl.SoulDictServiceImpl;
import org.dromara.soul.admin.vo.SoulDictVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

/**
 * Test cases for SoulDictService.
 *
 * @author dengliming
 */
@RunWith(MockitoJUnitRunner.class)
public class SoulDictServiceTest {

    @InjectMocks
    private SoulDictServiceImpl soulDictService;

    @Mock
    private SoulDictMapper soulDictMapper;

    @Test
    public void testFindByType() {
        SoulDictDO soulDictDO = buildSoulDictDO();
        given(this.soulDictMapper.selectByQuery(any())).willReturn(Arrays.asList(soulDictDO));
        List<SoulDictVO> soulDictVOList = this.soulDictService.list("rule");
        assertEquals(1, soulDictVOList.size());
        assertEquals(soulDictDO.getId(), soulDictVOList.get(0).getId());
    }

    @Test
    public void testFindById() {
        SoulDictDO soulDictDO = buildSoulDictDO();
        given(this.soulDictMapper.selectById(eq("123"))).willReturn(soulDictDO);
        SoulDictVO soulDictVO = this.soulDictService.findById("123");
        assertNotNull(soulDictVO);
        assertEquals(soulDictDO.getId(), soulDictVO.getId());
    }

    @Test
    public void testCreateOrUpdate() {
        given(this.soulDictMapper.insertSelective(any())).willReturn(1);
        assertThat(this.soulDictService.createOrUpdate(buildSoulDictDTO()), greaterThan(0));
    }

    @Test
    public void testDeleteSoulDicts() {
        given(this.soulDictMapper.delete(eq("123"))).willReturn(1);
        int count = soulDictService.deleteSoulDicts(Arrays.asList("123"));
        assertThat(count, greaterThan(0));
    }

    @Test
    public void testBatchEnabled() {
        BatchCommonDTO batchCommonDTO = new BatchCommonDTO();
        batchCommonDTO.setEnabled(false);
        batchCommonDTO.setIds(Arrays.asList("123"));
        given(this.soulDictMapper.enabled(eq(batchCommonDTO.getIds()), eq(batchCommonDTO.getEnabled()))).willReturn(1);
        assertThat(this.soulDictService.enabled(batchCommonDTO.getIds(), batchCommonDTO.getEnabled()), greaterThan(0));
    }

    private SoulDictDTO buildSoulDictDTO() {
        return buildSoulDictDTO(null);
    }

    private SoulDictDTO buildSoulDictDTO(final String id) {
        SoulDictDTO soulDictDTO = new SoulDictDTO();
        soulDictDTO.setId("123");
        soulDictDTO.setDesc("test");
        soulDictDTO.setId(id);
        soulDictDTO.setSort(1);
        soulDictDTO.setDesc("test");
        soulDictDTO.setDictCode("t_dict_1");
        soulDictDTO.setDictName("t_d_v");
        soulDictDTO.setEnabled(false);
        soulDictDTO.setType("rule");
        return soulDictDTO;
    }

    private SoulDictDO buildSoulDictDO() {
        SoulDictDO soulDictDO = SoulDictDO.buildSoulDictDO(buildSoulDictDTO());
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        soulDictDO.setDateCreated(now);
        soulDictDO.setDateUpdated(now);
        return soulDictDO;
    }
}
