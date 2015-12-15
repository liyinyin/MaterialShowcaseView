# MaterialShowcaseView
A ShowcaseView for Android

This library is base on the [version] [2] for [MaterialShowcaseView] [1].

# Usage

**Include the library as local library project or add the dependency in your build.gradle.**

```
dependencies {
    compile 'com.lion.materialshowcaseview:library:1.0.0'
}
```

### ShowcaseView Sub Sequence 

![ShowcaseView Sub Sequence](/gif/sub_sequence_showcaseview.gif)

### TitleBar Activity switch Non TitleBar Activity

![TitleBar Activity switch Non TitleBar Activity](/gif/fix_titlebar.gif)

# Added
- ShowcaseView Sub Sequence.
- zh-rCN support.
- Text font support.

# Modified
- ShowcaseView was modified, add arrow and change content location.
- For big view need to show ShowcaseView, add interface to set ShowcaseView circle radius and location area.
    ```LEFT_EDGE, LEFT, CENTER, RIGHT, RIGHT_EDGE```

# Fixed
- TitleBar Activity switch Non TitleBar Activity, ShowcaseView has Y-offset. Need to call method manual.

[1]: https://github.com/deano2390/MaterialShowcaseView
[2]: https://github.com/deano2390/MaterialShowcaseView/tree/5ba8465d441a9c38668d45b0838b7ffd0e7e6cdc
