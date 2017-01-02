/*
 * Copyright (c) 2015 by k3b.
 *
 * This file is part of AndroFotoFinder.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>
 */

package de.k3b.io;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by k3b on 01.09.2015.
 */
public class GalleryFilterParameterTest {

    public static final String FILTER_STRING_FULL_EXAMPLE = "1.23,2.34;3.45,4.56;2001-02-03,2005-12-31;/some/path/;q,^;filter;tag1,tag2,tag3;utag1,utag2,utag3;notags;3;";

    @Test
    public void toStringFullTest() {
        GalleryFilterParameter sut = new GalleryFilterParameter();
        sut.setLatitude(1.23,3.45).setLogitude(2.34, 4.56);
        sut.setDateMin(Date.valueOf("2001-02-03").getTime()).setDateMax(Date.valueOf("2005-12-31").getTime());
        sut.setPath("/some/path/");
        sut.setSortID('q');
        sut.setSortAscending(true);
        sut.setInAnyField("filter");
        sut.setTagsAllIncluded(GalleryFilterParameter.convertList("tag1,tag2,tag3"));
        sut.setTagsAllExcluded(GalleryFilterParameter.convertList("utag1,utag2 utag3"));
        sut.setVisibility(IGalleryFilter.VISIBILITY_PRIVATE_PUBLIC);
        sut.setWithNoTags(true);

        assertEquals(FILTER_STRING_FULL_EXAMPLE, sut.toString());
        assertEquals("not empty", false,  GalleryFilterParameter.isEmpty(sut));
    }

    @Test
    public void toStringEmptyTest() {
        GalleryFilterParameter sut = new GalleryFilterParameter();
        assertEquals(";;;;;;;;;", sut.toString());
        assertEquals("empty", true,  GalleryFilterParameter.isEmpty(sut));
    }

    @Test
    public void shouldParseFull() {
        GalleryFilterParameter sutParsed = GalleryFilterParameter.parse(FILTER_STRING_FULL_EXAMPLE, new GalleryFilterParameter());
        GalleryFilterParameter sut = new GalleryFilterParameter().get(sutParsed);
        assertEquals(FILTER_STRING_FULL_EXAMPLE, sut.toString());
    }

    @Test
    public void shouldParseFullNoGeo() {
        GalleryFilterParameter sutParsed = GalleryFilterParameter.parse("n;;2001-02-03,2005-12-31;/some/path/;q,^;a;b;c;notags;3;", new GalleryFilterParameter());
        GalleryFilterParameter sut = new GalleryFilterParameter().get(sutParsed);
        assertEquals("noGeoInfo;;2001-02-03,2005-12-31;/some/path/;q,^;a;b;c;notags;3;", sut.toString());
    }
}