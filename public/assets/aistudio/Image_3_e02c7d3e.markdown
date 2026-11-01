---
name: Serene Balance
colors:
  surface: '#f9f9ff'
  surface-dim: '#cfdaf2'
  surface-bright: '#f9f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f0f3ff'
  surface-container: '#e7eeff'
  surface-container-high: '#dee8ff'
  surface-container-highest: '#d8e3fb'
  on-surface: '#111c2d'
  on-surface-variant: '#414844'
  inverse-surface: '#263143'
  inverse-on-surface: '#ecf1ff'
  outline: '#717974'
  outline-variant: '#c1c8c3'
  surface-tint: '#426556'
  primary: '#274a3c'
  on-primary: '#ffffff'
  primary-container: '#3f6253'
  on-primary-container: '#b5dcc9'
  inverse-primary: '#a9cfbc'
  secondary: '#41674c'
  on-secondary: '#ffffff'
  secondary-container: '#c2edcb'
  on-secondary-container: '#466d52'
  tertiary: '#772a16'
  on-tertiary: '#ffffff'
  tertiary-container: '#96412a'
  on-tertiary-container: '#ffc6b8'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#c4ebd8'
  primary-fixed-dim: '#a9cfbc'
  on-primary-fixed: '#002116'
  on-primary-fixed-variant: '#2b4d3f'
  secondary-fixed: '#c2edcb'
  secondary-fixed-dim: '#a7d1b0'
  on-secondary-fixed: '#00210f'
  on-secondary-fixed-variant: '#294e36'
  tertiary-fixed: '#ffdbd2'
  tertiary-fixed-dim: '#ffb4a1'
  on-tertiary-fixed: '#3c0800'
  on-tertiary-fixed-variant: '#7c2e19'
  background: '#f9f9ff'
  on-background: '#111c2d'
  surface-variant: '#d8e3fb'
typography:
  display:
    fontFamily: Plus Jakarta Sans
    fontSize: 40px
    fontWeight: '600'
    lineHeight: 48px
    letterSpacing: -0.02em
  display-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '600'
    lineHeight: 36px
    letterSpacing: -0.015em
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: -0.015em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
  title-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '600'
    lineHeight: 22px
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 26px
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 22px
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 18px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 11px
    fontWeight: '500'
    lineHeight: 14px
    letterSpacing: 0.04em
  stat-numeric:
    fontFamily: Plus Jakarta Sans
    fontSize: 34px
    fontWeight: '600'
    lineHeight: 40px
    letterSpacing: -0.03em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-tablet: 1.5rem
  gutter-desktop: 2rem
  margin: 1.25rem
  margin-tablet: 2rem
  margin-desktop: 3rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2.5rem
---

## Brand & Style

The design system is centered on mindful vitality, balance, and gentle progression. Tailored for individuals seeking a restorative, sustainable relationship with movement, recovery, and holistic health, the experience avoids aggressive, high-friction fitness patterns. Instead, it fosters clarity, emotional ease, and grounded motivation.

The aesthetic fuses warm organic minimalism with subtle tactile depth. Interfaces use generous breathing room, quiet surfaces, and intentional typography to minimize cognitive friction. Visual weight is concentrated in metrics and progress cues rather than decorative ornament, achieving an atmosphere that feels like a quiet sanctuary rather than a high-stimulus athletic tracker.

## Colors

The palette draws from natural, restorative elements: botanical greens, warm earth ceramics, and soft morning stone.

- **Primary (`#3F6253` Deep Sage) & Secondary (`#5B8266` Soft Sage):** Anchors primary actions, completed states, streak indicators, and mindful progress milestones.
- **Tertiary (`#E07A5F` Muted Terracotta):** Reserved for energetic metrics, heart rate zones, active timers, warnings, and high-impact micro-interactions.
- **Neutral (`#1E293B` Warm Slate):** Used strictly for high-contrast primary typography and icons. Secondary text shifts to a softened slate (`#64748B`), and muted hints to (`#94A3B8`).
- **Canvas & Surfaces:** Base canvas sits on `#FAF9F5` (warm cream), paired with floating component containers in `#FFFFFF`. Structural delineation relies on `#EBE8E1` (soft sand border).

## Typography

Plus Jakarta Sans governs the typography scale across all tiers. Its open letterforms and rounded geometric curves convey warmth without forfeiting analytical legibility.

- **Numerics & Vital Stats:** Use `stat-numeric` with tighter tracking (`-0.03em`) for metrics such as step counts, recovery scores, and resting heart rates.
- **Rhythm & Line Height:** Line heights are kept generous (typically 1.5× to 1.6× on body text) to ensure seamless scanning during workouts or active movement.
- **Microcopy & Tags:** Label tokens leverage moderate tracking (`0.02em` - `0.04em`) to maintain legibility when rendered in muted neutral shades or enclosed within pill chips.

## Layout & Spacing

The layout model is mobile-first, rooted in an 8pt base grid with a 4pt subgrid for precise micro-alignment.

- **Mobile Viewport:** Uses a 4-column fluid layout with `margin: 1.25rem` (20px) and `gutter: 1rem` (16px).
- **Tablet / Large Handheld:** Shifts to an 8-column layout with `margin: 2rem` and `gutter: 1.5rem`.
- **Desktop / Web Dashboard:** Expands to a 12-column layout capped at a maximum width of 1120px to preserve intimate, card-focused composition.
- **Vertical Spacing:** Generous component gap spacing ensures screens feel unhurried. Distinct wellness categories (e.g., Sleep, Mind, Activity) are separated by `space-xl`, while metric sub-cards inside grouped views rely on `space-md`.

## Elevation & Depth

Depth is established through soft ambient illumination rather than harsh projection drops, harmonizing with physical paper and ceramic surfaces.

- **Level 0 (Base Canvas):** `#FAF9F5`. Completely unraised; serves as the warm neutral foundation for the app.
- **Level 1 (Card & Section Containers):** `#FFFFFF` fill overlaid on the canvas, bounded by a 1px solid `#EBE8E1` border and accompanied by a diffused ambient shadow: `0px 4px 20px -2px rgba(30, 41, 59, 0.04)`.
- **Level 2 (Floating Action Bars / Active Cards):** `#FFFFFF` fill, bordered with `#EBE8E1`, elevated via `0px 10px 30px -4px rgba(30, 41, 59, 0.08)`.
- **Level 3 (Modals & Bottom Drawers):** `#FFFFFF` fill with an upward ambient wash: `0px -8px 36px 0px rgba(30, 41, 59, 0.10)`. Underlaid with a soft backdrop overlay of `rgba(30, 41, 59, 0.35)` with an 8px blur.
- **Interactive State Depth:** Cards gently elevate on tap/press with a scale of `0.99` and an internal border transition to `#3F6253` at 25% opacity.

## Shapes

The interface embraces organic curvature without losing structural definition.

- **Primary Cards & Containers:** Standardized with `rounded-lg` (16px) for standard widgets and `rounded-xl` (24px) for prominent primary summary cards and bottom sheets.
- **Buttons & Interactive Tags:** Primary interactive surfaces use fully rounded pill structures (`rounded-full`) or `rounded-lg` (16px) for block-level buttons.
- **Metric Rings & Gauge Visuals:** Gauge tracks and progress capsules feature full concentric radius ends, mirroring the tactile softness of the rest of the interface.

## Components

### Buttons
- **Primary:** Background `#3F6253`, label `#FFFFFF` (`label-lg`), height 52px, border radius 16px (or pill). Pressed state deepens to `#2E483D`.
- **Secondary / Soft:** Background `rgba(63, 98, 83, 0.08)`, text `#3F6253`, height 52px, border radius 16px. No border.
- **Tertiary Accent (Active Workout/Timer):** Background `#E07A5F`, text `#FFFFFF`, radius 16px.
- **Ghost:** Background transparent, text `#3F6253`, active feedback via warm slate hover/press tint.

### Cards
- **Tactile Metric Card:** Pure white (`#FFFFFF`) surface, 16px or 24px corner radius, 1px `#EBE8E1` perimeter stroke. Interior padding sits at `space-md` or `space-lg`. Metrics use `stat-numeric` in `#1E293B`, paired with a tertiary terracotta pill tag for real-time deltas.
- **Insight / Journal Card:** `#FAF9F5` tinted box placed inside cards, framed with soft sand outlines for secondary textual guidance.

### Chips & Filters
- **Filter Chip:** Height 36px, radius 100px. Inactive has `#FFFFFF` background, 1px border `#EBE8E1`, and `#64748B` label. Active has `#3F6253` background with white label, with no outline.

### Inputs & Search
- **Text Input:** Height 52px, background `#FFFFFF`, border 1.5px solid `#EBE8E1`, border radius 14px, text `#1E293B`, placeholder `#94A3B8`. Focus state shifts the border cleanly to `#3F6253` with zero glow ring.

### Checkboxes & Habit Toggles
- **Habit Check Circle:** 28px diameter circle. Unchecked has 2px stroke in `#EBE8E1`. Checked morphs to `#3F6253` fill with a crisp `#FFFFFF` checkmark, accompanied by a subtle 0.15s spring scale.

### Health Rings & Progress Bars
- **Progress Track:** Height 8px (standard) or 16px (segmented), background `#EBE8E1`, radius 100px. Active progress fills with `#5B8266` or `#E07A5F` based on strain/activity modality.