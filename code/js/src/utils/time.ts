export function timeSince(datetime: string): string {
  const currentDate = new Date();
  const pastDate = new Date(datetime);

  const timeDifference = currentDate.getTime() - pastDate.getTime();

  const timeUnits = [
    { unit: "year", milliseconds: 365 * 24 * 60 * 60 * 1000 },
    { unit: "month", milliseconds: 30 * 24 * 60 * 60 * 1000 },
    { unit: "day", milliseconds: 24 * 60 * 60 * 1000 },
    { unit: "hour", milliseconds: 60 * 60 * 1000 },
    { unit: "minute", milliseconds: 60 * 1000 },
    { unit: "second", milliseconds: 1000 },
  ];

  for (const unitData of timeUnits) {
    const { unit, milliseconds } = unitData;
    const count = Math.floor(timeDifference / milliseconds);

    if (count >= 1) {
      return `${count} ${unit}${count === 1 ? "" : "s"} ago`;
    }
  }

  return "Just now";
}

export function delay(delayInMs: number) {
  return new Promise((resolve) => {
    setTimeout(() => resolve(undefined), delayInMs);
  });
}
