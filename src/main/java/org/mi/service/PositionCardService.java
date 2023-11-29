package org.mi.service;

import org.mi.dao.PositionCardDao;

public class PositionCardService {
    private static PositionCardDao dao = new PositionCardDao();

    public String getName(String groupId) {
        return dao.getPositionCardNameByGroupId(groupId);
    }
}
