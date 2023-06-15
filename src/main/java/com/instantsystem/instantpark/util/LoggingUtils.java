package com.instantsystem.instantpark.util;

/**
 * Utility class for creating consistent log messages throughout the application.
 */
public class LoggingUtils {

    public static final String SPACE = " ";
    public static final String START = "start";
    public static final String END = "end";

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private LoggingUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generates an end phase log message for the calling method with given inputs.
     *
     * @param inputs variables to be logged
     * @return the formatted log message
     */
    public static String getEndMessage(Object... inputs) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return buildStringObjects(methodName, LoggingUtils.END, inputs).toString();
    }

    /**
     * Generates a log message for the calling method with given inputs.
     *
     * @param inputs variables to be logged
     * @return the formatted log message
     */
    public static String getMessage(Object... inputs) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return buildStringObjects(methodName, "", inputs).toString();
    }

    /**
     * Generates a start phase log message for the calling method with given inputs.
     *
     * @param inputs variables to be logged
     * @return the formatted log message
     */
    public static String getStartMessage(Object... inputs) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return buildStringObjects(methodName, LoggingUtils.START, inputs).toString();
    }

    /**
     * Builds the log message string with the given method name, phase, and inputs.
     *
     * @param methodName the name of the method
     * @param phase      the phase of execution (start, end, etc.)
     * @param inputs     the inputs to the method
     * @return the StringBuilder for the log message
     */
    private static StringBuilder buildStringObjects(String methodName, String phase, Object... inputs) {
        StringBuilder builder = new StringBuilder(phase).append(LoggingUtils.SPACE);
        builder.append(methodName).append(LoggingUtils.SPACE).append("(");
        boolean hasArgs = false;
        for (Object o : inputs) {
            hasArgs = true;
            try {
                if (o != null) {
                    builder.append("<").append(o.getClass().getSimpleName()).append(">");
                    builder.append(o.toString());
                    builder.append(", ");
                } else {
                    builder.append("null, ");
                }

            } catch (Exception e) {
                builder.append("null, ");
            }
        }
        if (hasArgs) {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append(")");
        return builder;
    }
}