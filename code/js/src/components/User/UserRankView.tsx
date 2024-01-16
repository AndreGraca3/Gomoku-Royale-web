import InfoDisplayer from "../InfoDisplayer";

export function UserRankView({ rank }) {
  return (
    <InfoDisplayer title={rank.name} imgUrl={rank.iconUrl} />
  );
}
