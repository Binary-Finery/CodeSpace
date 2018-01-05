package spencerstudios.com.codespace;

class ReportSpam {

    private String reportTitle;
    private long reportTimestamp;

    ReportSpam(String reportTitle, long reportTimestamp) {
        this.reportTitle = reportTitle;
        this.reportTimestamp = reportTimestamp;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public long getReportTimestamp() {
        return reportTimestamp;
    }

    public void setReportTimestamp(long reportTimestamp) {
        this.reportTimestamp = reportTimestamp;
    }
}
