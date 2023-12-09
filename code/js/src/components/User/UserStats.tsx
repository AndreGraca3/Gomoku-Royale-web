import { IndentedParagraph } from "../IndentedParagraph";
import { UnderlinedHeader } from "../UnderlinedHeader";

export function UserStatsView({ userStats }) {
  const winStats = userStats.winStats;
  const matchesStats = userStats.matchesStats;

  return (
    <div className="vertical-align:middle">
      <div>
        <UnderlinedHeader>🏆 Win Stats</UnderlinedHeader>
        <IndentedParagraph>{`📈 Wins: ${winStats.totalWins}`}</IndentedParagraph>
        <IndentedParagraph>{`📉 Loses: ${winStats.loses}`}</IndentedParagraph>
        <IndentedParagraph>{`🤝 Draws: ${winStats.draws}`}</IndentedParagraph>
        <IndentedParagraph>{`📈⚫ Wins as black: ${winStats.winsAsBlack}`}</IndentedParagraph>
        <IndentedParagraph>{`📈⚪ Wins as white: ${winStats.winsAsWhite}`}</IndentedParagraph>
        <IndentedParagraph>{`📊 Win rate: ${winStats.winRate}`}</IndentedParagraph>
      </div>
      <div>
        <UnderlinedHeader>📊 Matches Stats</UnderlinedHeader>
        <IndentedParagraph>{`📝 Total matches: ${matchesStats.totalMatches}`}</IndentedParagraph>
        <IndentedParagraph>{`⚫ Matches as black: ${matchesStats.matchesAsBlack}`}</IndentedParagraph>
        <IndentedParagraph>{`⚪ Matches as white: ${matchesStats.matchesAsWhite}`}</IndentedParagraph>
      </div>
    </div>
  );
}
