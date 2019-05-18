package com.bpms.cmn.service;

import com.google.common.collect.Maps;
import com.bpms.cmn.entity.ProgressInfo;
import com.bpms.core.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 进度条服务类
 */
@Service
public class ProgressService {
    /**
     * 进度监控查询map
     */
    private Map<String, ProgressInfo> progressInfoMap = Maps.newHashMap();

    /**
     * 启动进度
     *
     * @param progressId 进度id 全局唯一
     */
    public void start(@NotNull(message = "进度唯一ID不能为空。") String progressId, ProgressInfo progressInfo) {
        //验证进度是否已经添加
        if (progressInfoMap.containsKey(progressId)) {
            throw new ServiceException(MessageFormat.format("此进度已经存在（progressId:{0}）。", progressId));
        }
        progressInfoMap.put(progressId, progressInfo == null ? new ProgressInfo() : progressInfo);
    }

    /**
     * 根据进度ID获取进度信息
     *
     * @param progressId 进度ID
     * @return 进度信息
     */
    public ProgressInfo show(@NotNull(message = "进度唯一ID不能为空。") String progressId) {
        ProgressInfo progressInfo;
        //如果进度ID KEY不存在场合
        if (!progressInfoMap.containsKey(progressId)) {
            try {
                //给定3秒缓冲时间
                Thread.sleep(3000L);
            } catch (InterruptedException ignored) {
            }
            progressInfo = progressInfoMap.get(progressId);
            //无法获取进度信息，直接返回null
            if (progressInfo == null) {
                return null;
            }
        }
        return progressInfoMap.get(progressId);
    }

    /**
     * 更新进度信息
     *
     * @param progressId   进度ID
     * @param progressInfo 进度信息
     */
    public synchronized void progress(@NotNull(message = "进度唯一ID不能为空。") String progressId, @NotNull(message = "进度信息不能为空。") ProgressInfo progressInfo) {
        progressInfoMap.put(progressId, progressInfo);
    }

    /**
     * 获取当前progressId进度信息
     */
    public synchronized ProgressInfo getProgress(@NotNull(message = "进度唯一ID不能为空。") String progressId) {
        return progressInfoMap.get(progressId);
    }

    /**
     * 移除进度
     *
     * @param progressId 进度ID
     */
    public void removeProgress(@NotNull(message = "进度唯一ID不能为空。") String progressId) {
        progressInfoMap.remove(progressId);
    }

    /**
     * 根据进度ID更新进度信息
     *
     * @param progressId 进度ID
     * @param currentNum 进度数
     */
    public synchronized void updateProgress(String progressId, int currentNum) {
        if (StringUtils.isNotEmpty(progressId)) {
            ProgressInfo progressInfo = progressInfoMap.get(progressId);
            if (progressInfo != null) {
                progressInfo.setCurrentNum(progressInfo.getCurrentNum() + currentNum);
                progressInfo.setProgressText("已完成" + progressInfo.getCurrentNum() + "条/共" + progressInfo.getTotalNum() + "条");
                progressInfoMap.put(progressId, progressInfo);
            }
        }
    }
}