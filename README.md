# go-language-support-proto
Prototype for Go Language Support

There had been some Go support for NetBeans 7, which had been incomplete (due to comments), and I don't have the source code for it.

Now I've started a new implementation from Golang.g4 grammar found in the antlr4 repositories.

The grammar file is under BSD license, so that should not cause problems. My only modification has been to remove the "-> skip" directives.

As an implementation guide I basically used http://wiki.netbeans.org/Netbeans_Rcp_Antlr_Integration_Index, but I had already to add some LanguageRegistration to get highlighting working.

Currently I'm working on the formatting part. Now I have to use the parser, and now I'm getting a conflict with the LanguageRegistration, as CSL ParserResult is expected instead of Parsing API Parser.Result.

<b>This README should be modified to contain or link to some upgraded documentation after finishing.</b>
