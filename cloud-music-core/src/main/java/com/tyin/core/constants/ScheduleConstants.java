package com.tyin.core.constants;

/**
 * @author Tyin
 * @date 2022/7/7 16:57
 * @description ...
 */
public class ScheduleConstants {
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";
    /**
     * 默认
     */
    public static final String MISFIRE_DEFAULT = "0";

    /**
     * 立即触发执行
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /**
     * 触发一次执行
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * 不触发立即执行
     */
    public static final String MISFIRE_DO_NOTHING = "3";

    public enum Status {
        /**
         * 正常
         */
        NORMAL(Boolean.FALSE),
        /**
         * 暂停
         */
        PAUSE(Boolean.TRUE);

        private final Boolean value;

        Status(Boolean value) {
            this.value = value;
        }

        public Boolean getValue() {
            return value;
        }
    }
}
