# WebCrawler
Basic web crawler from a news website then send articles links to an email address.

Bug:
PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
Resolved: add certificate into java trust store (https://stackoverflow.com/questions/21076179/pkix-path-building-failed-and-unable-to-find-valid-certification-path-to-requ)
