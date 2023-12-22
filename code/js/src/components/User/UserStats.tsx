import { IndentedParagraph } from "../IndentedParagraph";

export function UserStatsView({ userStats }) {
  const winStats = userStats.winStats;
  const matchesStats = userStats.matchesStats;

  return (
    <div className="vertical-align:middle">
      <div>
        <h1 className="underline">🏆 Win Stats</h1>
        <IndentedParagraph>{`📈 Wins: ${winStats.totalWins}`}</IndentedParagraph>
        <IndentedParagraph>{`📉 Loses: ${winStats.loses}`}</IndentedParagraph>
        <IndentedParagraph>{`🤝 Draws: ${winStats.draws}`}</IndentedParagraph>
        <IndentedParagraph>{`📈⚫ Wins as black: ${winStats.winsAsBlack}`}</IndentedParagraph>
        <IndentedParagraph>{`📈⚪ Wins as white: ${winStats.winsAsWhite}`}</IndentedParagraph>
        <IndentedParagraph>{`📊 Win rate: ${winStats.winRate}`}</IndentedParagraph>
      </div>
      <div>
        <h1 className="underline">📊 Matches Stats</h1>
        <IndentedParagraph>{`📝 Total matches: ${matchesStats.totalMatches}`}</IndentedParagraph>
        <IndentedParagraph>{`⚫ Matches as black: ${matchesStats.matchesAsBlack}`}</IndentedParagraph>
        <IndentedParagraph>{`⚪ Matches as white: ${matchesStats.matchesAsWhite}`}</IndentedParagraph>
      </div>
    </div>
  );
}
