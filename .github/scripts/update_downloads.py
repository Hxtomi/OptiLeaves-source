import json
from pathlib import Path
import xml.etree.ElementTree as ET
from urllib.request import Request, urlopen

PROJECT_IDS = ("ChDudAun", "5tucHyQx")
BADGE = Path(__file__).resolve().parents[1] / "badges" / "modrinth.svg"


def main():
    total = 0
    for project_id in PROJECT_IDS:
        request = Request(
            f"https://api.modrinth.com/v2/project/{project_id}",
            headers={"User-Agent": "Hxtomi/OptiLeaves-source (GitHub download badge)"},
        )
        with urlopen(request, timeout=30) as response:
            project = json.load(response)
        count = project["downloads"]
        if project["id"] != project_id or type(count) is not int or count < 0:
            raise ValueError("Unexpected Modrinth project response")
        total += count

    label = str(total)
    for divisor, suffix in ((1_000_000_000, "B"), (1_000_000, "M"), (1_000, "k")):
        if total >= divisor:
            value = total / divisor
            label = f"{value:.1f}".rstrip("0").rstrip(".") if value < 10 else f"{value:.0f}"
            label += suffix
            break

    request = Request(
        f"https://img.shields.io/badge/Modrinth-{label}-177c47?style=flat",
        headers={"User-Agent": "Hxtomi/OptiLeaves-source (GitHub download badge)"},
    )
    with urlopen(request, timeout=30) as response:
        svg = response.read().decode("utf-8")

    namespace = "http://www.w3.org/2000/svg"
    badge = ET.fromstring(svg)
    title = badge.find(f"{{{namespace}}}title")
    if (
        badge.tag != f"{{{namespace}}}svg"
        or badge.get("height") != "20"
        or badge.get("aria-label") != f"Modrinth: {label}"
        or title is None
    ):
        raise ValueError("Unexpected Shields badge response")

    # Use Shields' sizing and style, keeping the exact combined total accessible.
    badge.set("aria-label", f"Modrinth: {total:,} downloads")
    title.text = f"Modrinth: {total:,} downloads, both projects combined"
    ET.register_namespace("", namespace)
    svg = ET.tostring(badge, encoding="unicode") + "\n"
    BADGE.parent.mkdir(parents=True, exist_ok=True)
    BADGE.write_text(svg, encoding="utf-8")
    print(f"Modrinth combined downloads: {total}")


if __name__ == "__main__":
    main()
