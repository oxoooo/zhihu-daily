/*
 *         DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 *  Copyright (C) 2015 XiNGRZ <xxx@oxo.ooo>
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 */

package ooo.oxo.zhihu.daily.api.model;

import java.util.Date;
import java.util.List;

public class News {

    public Date date;

    public List<Abstract> stories;

    public class Abstract {

        public int id;

        public String title;

        public List<String> images;

    }

}
