package spencerstudios.com.codespace;

class ReportSpam {

    private String reportTitle;
    private String reportContributor;
    private long reportTimestamp;

    ReportSpam(String reportTitle, String reportContributor, long reportTimestamp) {
        this.reportTitle = reportTitle;
        this.reportTimestamp = reportTimestamp;
        this.reportContributor = reportContributor;
    }

    public String getReportContributor() {
        return reportContributor;
    }

    public void setReportContributor(String reportContributor) {
        this.reportContributor = reportContributor;
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
